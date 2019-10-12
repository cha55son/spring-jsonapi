package com.chasonchoate.jsonapi.repo_pattern.common

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ResponseBody

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Controller
@ResponseBody
annotation class JSONAPIController
