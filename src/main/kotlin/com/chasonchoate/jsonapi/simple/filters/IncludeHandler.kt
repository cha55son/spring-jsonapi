package com.chasonchoate.jsonapi.simple.filters

import com.chasonchoate.jsonapi.JSONAPIDocumentBase
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

//@ControllerAdvice
//class IncludeResponseBodyAdvice : ResponseBodyAdvice<JSONAPIDocumentBase> {
//    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
//        return true
//    }
//
//    override fun beforeBodyWrite(body: JSONAPIDocumentBase?, returnType: MethodParameter, selectedContentType: MediaType, selectedConverterType: Class<out HttpMessageConverter<*>>, request: ServerHttpRequest, response: ServerHttpResponse): JSONAPIDocumentBase? {
//        /**
//         * One obvious approach here is to follow links for relations.
//         * This presents issues b/c we now have to maintain state while
//         * we make the additional internal HTTP calls. Seems like this
//         * alone advocates for a repository model.
//         */
//        return body
//    }
//
//}
