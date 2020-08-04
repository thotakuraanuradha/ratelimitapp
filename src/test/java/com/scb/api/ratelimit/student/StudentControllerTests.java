package com.scb.api.ratelimit.student;

import com.scb.api.ratelimit.student.common.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    private static final String POST_URI = "/api/v1/student";
    private static final String GET_URI = "/api/v1/student/1";


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void whenRequestNotExceedingCapacity_thenReturnOkResponse() {
        StudentDto studentdto = new StudentDto();
       ResponseEntity response= this.restTemplate.postForEntity(POST_URI,studentdto, String.class);
        HttpStatus status = response.getStatusCode();
        ResponseEntity response2= this.restTemplate.postForEntity(POST_URI,studentdto, String.class);
        HttpStatus status2 = response.getStatusCode();
        ResponseEntity response3= this.restTemplate.postForEntity(POST_URI,studentdto, String.class);
        HttpStatus status3 = response.getStatusCode();

        assertEquals(429,status3);



    }


    @Test
    public void whenGetRequestNotExceedingCapacity_thenReturnOkResponse() {
        StudentDto studentdto = new StudentDto();
        ResponseEntity response= this.restTemplate.getForEntity(GET_URI, String.class);
        HttpStatus status = response.getStatusCode();
        ResponseEntity response2= this.restTemplate.getForEntity(GET_URI, String.class);
        HttpStatus status2 = response.getStatusCode();
        ResponseEntity response3= this.restTemplate.getForEntity(GET_URI, String.class);
        HttpStatus status3 = response.getStatusCode();

        assertEquals(HttpStatus.TOO_MANY_REQUESTS,status2);

    }
}
