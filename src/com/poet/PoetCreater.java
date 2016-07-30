package com.poet;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.FieldSpec.Builder;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class PoetCreater {

	private static Map<String, ClassName> classTypes = new HashMap<String, ClassName>();
	
	static {
		classTypes.put("Entity", ClassName.get("javax.persistence", "Entity"));
		classTypes.put("GeneratedValue", ClassName.get("javax.persistence", "GeneratedValue"));
		classTypes.put("GenerationType", ClassName.get("javax.persistence", "GenerationType"));
		classTypes.put("Id", ClassName.get("javax.persistence", "Id"));
		classTypes.put("Table", ClassName.get("javax.persistence", "Table"));
		classTypes.put("JpaRepository", ClassName.get("org.springframework.data.jpa.repository", "JpaRepository"));
		classTypes.put("Service", ClassName.get("org.springframework.stereotype", "Service"));
		classTypes.put("Autowired", ClassName.get("org.springframework.beans.factory.annotation", "Autowired"));
	}
	
	/**
	 * 创建get方法
	 */
	private MethodSpec createGetFieldMethod(Field field) {
		char firstChar = (char) (field.getFieldName().charAt(0) - (char) 32);
		MethodSpec methodSpec = MethodSpec.methodBuilder("get" + firstChar + field.getFieldName().substring(1))
			    .addModifiers(Modifier.PUBLIC)
			    .returns(field.getFieldType())
			    .addStatement("return $N", field.getFieldName())
			    .build();
		return methodSpec;
	}

	/**
	 * 创建set方法
	 */
	private MethodSpec createSetFieldMethod(Field field) {
		char firstChar = (char) (field.getFieldName().charAt(0) - (char) 32);
		MethodSpec methodSpec = MethodSpec.methodBuilder("set" + firstChar + field.getFieldName().substring(1))
			    .addModifiers(Modifier.PUBLIC)
			    .addParameter(field.getFieldType(), field.getFieldName())
			    .returns(void.class)
			    .addStatement("this.$N = $N", field.getFieldName(), field.getFieldName())
			    .build();
		return methodSpec;
	}
	
	/**
	 * 创建domain类
	 */
	public void createDomain(TableInfo tableInfo) {
		List<FieldSpec> fieldSpecs = new ArrayList<>();
		List<MethodSpec> methodsSpecs = new ArrayList<>();
		
		List<Field> fields = tableInfo.getFields();
		for (Field field : fields) {
			
			Builder builder = FieldSpec.builder(field.getFieldType(), field.getFieldName())
				    .addModifiers(Modifier.PRIVATE);
			
			if (field.isPrimaryKey()) {
				AnnotationSpec idAnnotationSpec = AnnotationSpec.builder(classTypes.get("Id"))
						.build();
				
				AnnotationSpec generatedValueAnnotationSpec = AnnotationSpec.builder(classTypes.get("GeneratedValue"))
						.addMember("strategy", "$T.$N", classTypes.get("GenerationType"), "AUTO")
						.build();
				builder.addAnnotation(idAnnotationSpec);
				builder.addAnnotation(generatedValueAnnotationSpec);
			}
			
			fieldSpecs.add(builder.build());
			
			methodsSpecs.add(createGetFieldMethod(field));
			methodsSpecs.add(createSetFieldMethod(field));
		}

		AnnotationSpec entityAnnotationSpec = AnnotationSpec.builder(classTypes.get("Entity")).build();
		AnnotationSpec tableAnnotationSpec = AnnotationSpec.builder(classTypes.get("Table"))
		        .addMember("name", "$S", tableInfo.getTableName())
		        .build();
		
		TypeSpec typeSpec = TypeSpec.classBuilder(tableInfo.getClassNmae())
		    .addModifiers(Modifier.PUBLIC)
		    .addFields(fieldSpecs)
		    .addMethods(methodsSpecs)
		    .addAnnotation(entityAnnotationSpec)
		    .addAnnotation(tableAnnotationSpec)
		    .build();

		JavaFile javaFile = JavaFile.builder(Main.PACKAGE_DOMAIN, typeSpec)
		    .build();

		try {
			javaFile.writeTo(new File(Main.SAVE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建Repository
	 */
	public void createRepository(TableInfo tableInfo) {
		ClassName domainClassName = ClassName.get(Main.PACKAGE_DOMAIN, tableInfo.getClassNmae());
		ClassName longClassName = ClassName.get("java.lang", "Long");
		ParameterizedTypeName type = ParameterizedTypeName.get(classTypes.get("JpaRepository"), domainClassName, longClassName);
		
		// 统一返回Doamin
		List<MethodSpec> methosSpecs = new ArrayList<>();
		for (Field field : tableInfo.getFields()) {
			char firstChar = (char) (field.getFieldName().charAt(0) - (char) 32);
			MethodSpec methodSpec = MethodSpec.methodBuilder("findBy" + firstChar + field.getFieldName().substring(1))
					.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
					.addParameter(field.getFieldType(), field.getFieldName())
					.returns(domainClassName)
					.build();
			methosSpecs.add(methodSpec);
		}
		
		TypeSpec typeSpec = TypeSpec.interfaceBuilder(tableInfo.getClassNmae() + Main.REPOSITORY_SUFFIX)
			    .addModifiers(Modifier.PUBLIC)
			    .addSuperinterface(type)
			    .addMethods(methosSpecs)
			    .build();
		
		JavaFile javaFile = JavaFile.builder(Main.PACKAGE_REPOSITORY, typeSpec)
			    .build();
		
		try {
			javaFile.writeTo(new File(Main.SAVE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建Service
	 */
	public void createService(TableInfo tableInfo) {
		ClassName domainClassName = ClassName.get(Main.PACKAGE_DOMAIN, tableInfo.getClassNmae());
		
		AnnotationSpec autowiredAnnotationSpec = AnnotationSpec.builder(classTypes.get("Autowired"))
				.build();
		
		String fieldName = firstToLowerCase(tableInfo.getClassNmae()) + Main.REPOSITORY_SUFFIX;
		
		FieldSpec fieldSpec = FieldSpec.builder(ClassName.get(Main.PACKAGE_REPOSITORY, tableInfo.getClassNmae() + Main.REPOSITORY_SUFFIX), fieldName)
				.addAnnotation(autowiredAnnotationSpec)
				.addModifiers(Modifier.PRIVATE).build();
		
		// codeRecordRepository.findByProductCode(productCode);
		
		List<MethodSpec> methosSpecs = new ArrayList<>();
		for (Field field : tableInfo.getFields()) {
			char firstChar = (char) (field.getFieldName().charAt(0) - (char) 32);
			String methodName = "findBy" + firstChar + field.getFieldName().substring(1);
			MethodSpec methodSpec = MethodSpec.methodBuilder(methodName)
					.addModifiers(Modifier.PUBLIC)
					.addParameter(field.getFieldType(), field.getFieldName())
					.returns(domainClassName)
					.addStatement("return $N.$N($N)", fieldName, methodName, field.getFieldName())
					.build();
			methosSpecs.add(methodSpec);
		}
		
		AnnotationSpec serviceAnnotationSpec = AnnotationSpec.builder(classTypes.get("Service")).build();
		
		TypeSpec typeSpec = TypeSpec.classBuilder(tableInfo.getClassNmae() + Main.SERVICE_SUFFIX)
		    .addModifiers(Modifier.PUBLIC)
		    .addField(fieldSpec)
		    .addMethods(methosSpecs)
		    .addAnnotation(serviceAnnotationSpec)
		    .build();

		JavaFile javaFile = JavaFile.builder(Main.PACKAGE_SERVICE, typeSpec)
		    .build();

		try {
			javaFile.writeTo(new File(Main.SAVE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 首字母小写
	 */
	private String firstToLowerCase(String content) {
		char c = content.charAt(0);
		c = (char) (c + (char) 32);
		return c + content.substring(1);
	}
	
}
