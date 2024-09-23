package  com.asianpaints.apse.service_engineer


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


 data class Role(
     @JsonProperty("id")
     val id: Long = 0,
     @JsonProperty("name")
     val name: String
)
