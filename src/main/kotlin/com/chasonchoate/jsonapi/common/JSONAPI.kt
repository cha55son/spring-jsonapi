package com.chasonchoate.jsonapi.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class JSONAPIDocumentBase {
    var included: List<JSONAPIResource>? = null
    var meta: Map<String, Any>? = null
    var links: Map<String, JSONAPILink>? = null
}
class JSONAPIResourceDocument(var data: JSONAPIResource) : JSONAPIDocumentBase()
class JSONAPIResourcesDocument(var data: List<JSONAPIResource>) : JSONAPIDocumentBase()
class JSONAPIResourceIDDocument(var data: JSONAPIResourceID) : JSONAPIDocumentBase()
class JSONAPIResourceIDsDocument(var data: List<JSONAPIResourceID>) : JSONAPIDocumentBase()

open class JSONAPIResourceRelationshipBase {
    var meta: Map<String, Any>? = null
    var links: Map<String, JSONAPILink>? = null
}
class JSONAPIResourceHasOneRelationship(var data: JSONAPIResourceID?) : JSONAPIResourceRelationshipBase()
class JSONAPIResourceHasManyRelationship(var data: List<JSONAPIResourceID>?) : JSONAPIResourceRelationshipBase()

/**
 * TODO: Somehow allow strings here too
 */
class JSONAPILink(var href: String) {
    var meta: Map<String, Any>? = null
}

class JSONAPIResourceID(var id: String, var type: String)

@JsonInclude(JsonInclude.Include.NON_NULL)
class JSONAPIResource(var id: String, var type: String) {
    var attributes: Map<String, Any>? = null
    var relationships: Map<String, JSONAPIResourceRelationshipBase>? = null
    var meta: Map<String, Any>? = null
    var links: Map<String, JSONAPILink>? = null
}