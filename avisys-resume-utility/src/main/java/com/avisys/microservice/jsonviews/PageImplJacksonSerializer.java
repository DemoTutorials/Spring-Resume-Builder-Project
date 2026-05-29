package com.avisys.microservice.jsonviews;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageImplJacksonSerializer extends JsonSerializer<PageImpl<?>> {

	@Override
	public void serialize(PageImpl<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		serializers.getActiveView();
		gen.writeStartObject();
		gen.writeObjectField("content", page.getContent());
//		gen.

		gen.writeObjectFieldStart("pageable");
		gen.writeNumberField("offset", page.getPageable().getOffset());
		gen.writeNumberField("pageSize", page.getPageable().getPageSize());
		gen.writeEndObject();

		gen.writeBooleanField("first", page.isFirst());
		gen.writeBooleanField("last", page.isLast());
		gen.writeNumberField("totalPages", page.getTotalPages());
		gen.writeNumberField("totalElements", page.getTotalElements());
		gen.writeNumberField("pageNumber", (1 + page.getNumber()));
		gen.writeNumberField("numberOfElements", page.getNumberOfElements());

		gen.writeObjectFieldStart("sort");
		gen.writeBooleanField("empty", page.getContent().isEmpty());
		gen.writeBooleanField("sorted", page.getSort().isSorted());
		gen.writeBooleanField("unsorted", page.getSort().isUnsorted());
		gen.writeEndObject();

		Sort sort = page.getSort();

		for (Order sorting : sort) {
			gen.writeObjectFieldStart("sortObjects");
			gen.writeStringField("key", sorting.getProperty());
			gen.writeStringField("value", sorting.getDirection().name());
			gen.writeEndObject();
		}

		gen.writeEndObject();
		

	}

}
