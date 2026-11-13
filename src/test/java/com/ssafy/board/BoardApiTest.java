package com.ssafy.board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class BoardApiTest {
 @Autowired MockMvc mvc; @Autowired ObjectMapper json;
 @Test void lifecycle() throws Exception {
  var result=mvc.perform(post("/api/board").contentType("application/json").content("{\"title\":\"한글 제목\",\"writer\":\"ssafy\",\"content\":\"hello\"}"))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber()).andReturn();
  int id=json.readTree(result.getResponse().getContentAsString()).get("id").asInt();
  String url="/api/board/"+id;
  org.junit.jupiter.api.Assertions.assertEquals(url,result.getResponse().getHeader("Location"));
  mvc.perform(get("/api/board")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
  mvc.perform(get(url)).andExpect(status().isOk()).andExpect(jsonPath("$.viewCnt").value(1)).andExpect(jsonPath("$.regDate").isNotEmpty());
  mvc.perform(put(url).contentType("application/json").content("{\"id\":999,\"title\":\"changed\"}"))
   .andExpect(status().isOk()).andExpect(content().string(""));
  mvc.perform(get(url)).andExpect(jsonPath("$.title").value("changed")).andExpect(jsonPath("$.content").value("hello")).andExpect(jsonPath("$.viewCnt").value(2));
  mvc.perform(put(url).contentType("application/json").content("{}" )).andExpect(status().isOk());
  mvc.perform(delete(url)).andExpect(status().isNoContent()).andExpect(content().string(""));
  mvc.perform(get(url)).andExpect(status().isNotFound());
  mvc.perform(put(url).contentType("application/json").content("{}" )).andExpect(status().isNotFound());
  mvc.perform(delete(url)).andExpect(status().isNotFound());
 }
 @Test void invalidRequests() throws Exception {
  mvc.perform(get("/api/board/abc")).andExpect(status().isBadRequest());
  mvc.perform(post("/api/board").contentType("application/json").content("{" )).andExpect(status().isBadRequest());
  mvc.perform(post("/api/board").contentType("application/json")).andExpect(status().isBadRequest());
  mvc.perform(post("/api/board").contentType("text/plain").content("hello")).andExpect(status().isUnsupportedMediaType());
  mvc.perform(post("/api/board/1")).andExpect(status().isMethodNotAllowed());
 }
}
