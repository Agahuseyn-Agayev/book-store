package com.example.ingressbookstore.service

import com.example.ingressbookstore.entity.BookEntity
import com.example.ingressbookstore.entity.StudentEntity
import com.example.ingressbookstore.mapper.StudentMapper
import com.example.ingressbookstore.model.request.SaveStudentReadBookRequest
import com.example.ingressbookstore.model.request.SaveStudentRequest
import com.example.ingressbookstore.model.request.UpdateStudentRequest
import com.example.ingressbookstore.repository.AuthorRepository
import com.example.ingressbookstore.repository.BookRepository
import com.example.ingressbookstore.repository.StudentRepository
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static com.example.ingressbookstore.model.enums.Status.DELETED

class TestStudentService extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    @Autowired
    private BookRepository bookRepository
    @Autowired
    private AuthorRepository authorRepository
    private StudentRepository studentRepository
    private StudentService service

    def setup() {
        bookRepository = Mock()
        authorRepository = Mock()
        studentRepository = Mock()
        service = new StudentService(studentRepository, bookRepository, authorRepository)

    }

    def "TestGetStudentById success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(StudentEntity)

        when:
        def variable = service.getStudentById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        variable.name == entity.name
        variable.surname == entity.surname
        variable.age == entity.age
        variable.email == entity.email
    }

    def "TestGetStudentById error"() {
        given:
        def id = random.nextObject(Long)

        when:
        service.getStudentById(id)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        RuntimeException exception = thrown()
        exception.message == "STUDENT_NOT_FOUND"

    }

    def "TestSaveStudentRequest"() {
        given:
        def request = random.nextObject(SaveStudentRequest)
        def entity = random.nextObject(StudentEntity)
        def mapper = StudentMapper.builderStudentEntity(request)

        when:
        service.saveStudent(request)

        then:
        1 * studentRepository.save(mapper) >> Optional.of(entity)

    }

    def "TestUpdateStudentRequest success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(StudentEntity)
        def request = random.nextObject(UpdateStudentRequest)

        when:
        service.updateStudent(id, request)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        StudentMapper.updateStudent(entity, request)
    }

    def "TestUpdateStudentRequest error"() {
        given:
        def id = random.nextObject(Long)
        def request = random.nextObject(UpdateStudentRequest)

        when:
        service.updateStudent(id, request)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        0 * studentRepository.save(id)
        RuntimeException exception = thrown()
        exception.message == "STUDENT_NOT_FOUND"
    }

    def "TestDeleteStudentRequest success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(StudentEntity)

        when:
        service.deleteStudent(id)

        then:
        1 * studentRepository.findById(id) >> Optional.of(entity)
        1 * studentRepository.save(entity)
        entity.status == DELETED
    }

    def "TestDeleteStudentRequest error"() {
        given:
        def id = random.nextObject(Long)

        when:
        service.deleteStudent(id)

        then:
        1 * studentRepository.findById(id) >> Optional.empty()
        0 * studentRepository.save()
        RuntimeException exception = thrown()
        exception.message == "STUDENT_NOT_FOUND"
    }

    def "TestSaveStudentReadBookRequest success"() {
        given:
        def request = random.nextObject(SaveStudentReadBookRequest)
        def entity = random.nextObject(StudentEntity)
        def bookEntity = random.nextObject(BookEntity)

        when:
        studentRepository.findById(request.studentId)>>Optional.of(entity)
        bookRepository.findById(request.bookId)>>Optional.of(bookEntity)
        service.saveStudentReadBook(request)

        then:
        1 * bookRepository.findById(request.bookId) >> Optional.of(bookEntity)
        1 * studentRepository.findById(request.studentId) >> Optional.of(entity)
        1 * studentRepository.save(entity)
    }

    def "TestSaveStudentReadBookRequest error"() {
        given:
        def request = random.nextObject(SaveStudentReadBookRequest)

        when:
        studentRepository.findById(request.studentId)>>Optional.empty()
        bookRepository.findById(request.bookId)>>Optional.empty()
        service.saveStudentReadBook(request)
        then:
        1 * studentRepository.findById(request.studentId) >> Optional.empty()
        0 * studentRepository.save()
        RuntimeException exception = thrown()
        exception.message == "STUDENT_NOT_FOUND"
    }

}
