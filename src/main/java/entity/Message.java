package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author lizilin
 */
@Data
@AllArgsConstructor
public class Message {

    private Date date;

    private String username;

    private String message;


}
