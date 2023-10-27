package gongback.pureureumserver.domain.user

import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "#{@environment.getProperty('open-search.indices.users')}")
class UserDocument(
    @Id
    val id: Long,

    @Field(name = "name", type = FieldType.Text)
    val name: String,
)
