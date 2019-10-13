package com.chasonchoate.jsonapi

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class JSONAPIDocumentBase {
    var included: List<JSONAPIResource>? = null
    var meta: Map<String, Any>? = null
    var links: Map<String, JSONAPILink>? = null
}
open class JSONAPIResourceDocument(var data: JSONAPIResource?) : JSONAPIDocumentBase()
open class JSONAPIResourcesDocument(var data: List<JSONAPIResource>) : JSONAPIDocumentBase()
open class JSONAPIResourceIDDocument(var data: JSONAPIResourceID?) : JSONAPIDocumentBase()
open class JSONAPIResourceIDsDocument(var data: List<JSONAPIResourceID>) : JSONAPIDocumentBase()

@JsonInclude(JsonInclude.Include.NON_NULL)
open class JSONAPIResourceRelationshipBase {
    var meta: Map<String, Any>? = null
    var links: Map<String, JSONAPILink>? = null
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class JSONAPIResourceHasOneRelationship(var data: JSONAPIResourceID?) : JSONAPIResourceRelationshipBase()
@JsonInclude(JsonInclude.Include.NON_NULL)
class JSONAPIResourceHasManyRelationship(var data: List<JSONAPIResourceID>?) : JSONAPIResourceRelationshipBase()

/**
 * TODO: Somehow allow strings here too
 */
typealias JSONAPILink = String

class JSONAPIResourceID(var id: String, var type: String)

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class JSONAPIResource(var id: String, var type: String) {
    var attributes = mutableMapOf<String, Any>()
    var relationships = mutableMapOf<String, JSONAPIResourceRelationshipBase>()
    var meta = mutableMapOf<String, Any>()
    var links = mutableMapOf<String, JSONAPILink>()
}
