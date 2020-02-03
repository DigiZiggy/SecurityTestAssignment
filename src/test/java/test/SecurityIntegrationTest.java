package test;

import conf.MvcConfig;
import conf.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, SecurityConfig.class })
public class SecurityIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .apply(sharedHttpSession())
                .build();
    }

    @Test
    public void apiHomeDoesNotNeedAuthentication() throws Exception {
        mvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    public void redirectsToLoginForm() throws Exception {
        mvc.perform(get("/api/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void canLoginWithCorrectPassword() throws Exception {
        mvc.perform(formLogin("/login").user("user").password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mvc.perform(formLogin("/login").user("user").password("wrong_pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void employeeCanSeeMoreInfo() throws Exception {
        mvc.perform(get("/api/books/1").with(user("user").roles("USER")))
                .andExpect(status().isForbidden());

        mvc.perform(get("/api/books/1").with(user("employee").roles("EMPLOYEE")))
                .andExpect(status().isOk());
    }

    @Test
    public void canLogOut() throws Exception {
        mvc.perform(get("/api/users").with(user("employee").roles("EMPLOYEE")))
                .andExpect(status().isOk());

        mvc.perform(get("/api/books"))
                .andExpect(status().isOk());

        mvc.perform(logout("/api/logout"));

        mvc.perform(get("/api/users"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void logOutDoesNotRedirect() throws Exception {
        mvc.perform(logout("/api/logout"))
                .andExpect(status().isOk());
    }
}
