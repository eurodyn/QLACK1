<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
 <#if pojo.hasFieldJavaDoc(property)>    
    /**       
     * ${pojo.getFieldJavaDoc(property, 4)}
     */
</#if>	
	<#assign a>
		<#include "GetPropertyAnnotation.ftl"/>
	</#assign>
	<#if a?contains("eurodyn")>
		@Id
		${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${pojo.getGetterSignature(property)}() {
		if (this.${property.name} == null) {
             this.${property.name} = java.util.UUID.randomUUID().toString();
         }
		 
		 return this.${property.name};
		}			
	<#else>
		<#include "GetPropertyAnnotation.ftl"/>
		${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, jdk5)} ${pojo.getGetterSignature(property)}() {
			return this.${property.name};
		}	
	</#if>
    
    ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, jdk5)} ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#if>
</#foreach>
