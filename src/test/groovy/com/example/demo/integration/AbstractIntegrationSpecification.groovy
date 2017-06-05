package com.example.demo.integration

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by damz on 6/5/2017.
 */
class AbstractIntegrationSpecification extends Specification {

    @Shared def BASE_URL = "http://localhost:4000/api/v1"

    @Shared RestTemplate template = new TestRestTemplate()

    def sessionHeader

    def exchange(url, method, payload, clazz) {
        def entity = payload == null ?
                new HttpEntity(sessionHeader) :
                new HttpEntity(payload, sessionHeader)
        return template.exchange(url, method, entity, clazz)
    }

    def delete(url) {
        exchange(url, HttpMethod.DELETE, null, String.class)
    }

    def getForEntity(url, clazz) {
        return exchange(url, HttpMethod.GET, null, clazz)
    }

    def postForEntity(url, payload, clazz) {
        return exchange(url, HttpMethod.POST, payload, clazz)
    }

    def postForLocation(url, payload) {
        def response = postForEntity(url, payload, String.class)
        if (response.statusCode != HttpStatus.CREATED)
            return null
        return response.getHeaders().getLocation()
    }

    def put(url, payload) {
        template.put(url, new HttpEntity(payload, sessionHeader))
    }

    def putForResponseEntity(url, payload) {
        return exchange(url, HttpMethod.PUT, payload, String.class)
    }
}
