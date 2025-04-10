package com.example.asm_group_4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Staff")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Mã nhân viên không được để trống")
    String employeeCode;

    @NotBlank(message = "Họ tên không được để trống")
    String fullName;

    @NotNull(message = "Ngày sinh không được để trống")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dayOfBirth;

    @NotNull(message = "Giới tính không được để trống")
    Boolean gender;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    String phoneNumber;

    @NotBlank(message = "ID thẻ không được để trống")
    String idCard;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endDate;

    @NotNull(message = "Thời gian không được để trống")
    @Min(value = 1, message = "Thời gian phải lớn hơn 0")
    Integer duration;

    @NotNull(message = "Trạng thái không được để trống")
    Integer status;
}
