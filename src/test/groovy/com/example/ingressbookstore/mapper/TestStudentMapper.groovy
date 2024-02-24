package com.example.ingressbookstore.mapper

import com.example.ingressbookstore.entity.StudentEntity
import com.example.ingressbookstore.model.request.SaveStudentRequest
import com.example.ingressbookstore.model.request.UpdateStudentRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.ingressbookstore.mapper.StudentMapper.*
import static com.example.ingressbookstore.model.enums.Status.ACTIVE

class TestStudentMapper extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestStudentResponseBuild"() {
        given:
        def entity = random.nextObject(StudentEntity)

        when:
        def variable = builderStudentResponse(entity)

        then:
        variable.name == entity.name
        variable.surname == entity.surname
        variable.email == entity.email
        variable.age == entity.age
    }

    def "TestStudentBuilderEntity"() {
        given:
        def request = random.nextObject(SaveStudentRequest)

        when:
        def variable = builderStudentEntity(request)

        then:
        variable.name == request.name
        variable.surname == request.surname
        variable.age == request.age
        variable.email == request.email
        variable.status == ACTIVE
    }

    def "TestUpdateStudent"() {
        given:
        def request = random.nextObject(UpdateStudentRequest)
        def entity = random.nextObject(StudentEntity)

        when:
        updateStudent(entity, request)

        then:
        request.name == entity.name
        request.surname == entity.surname
        request.email == entity.email
        request.age == entity.age
    }

    def "TestGetStudents"(){
        given:
        def entity=random.nextObject(StudentEntity)

        when:
        def variable=getStudents(entity)

        then:
        variable.name==entity.name
        variable.surname==entity.surname
        variable.email==entity.email
        variable.age==entity.age
    }
}
