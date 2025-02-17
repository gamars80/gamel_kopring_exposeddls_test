package com.example.gamel_kopring_expoest_test.annotaion

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("authentication.principal.roleType == T(com.example.gamel_kopring_expoest_test.enums.RoleType).ADMIN.name")
annotation class AdminOnly