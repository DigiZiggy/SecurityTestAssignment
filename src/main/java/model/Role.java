package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class Role {

    @JsonIgnore
    private Integer id;

    private String name;

    @JsonIgnore
    private List<String> users;
}
