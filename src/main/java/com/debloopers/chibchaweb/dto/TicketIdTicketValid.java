package com.debloopers.chibchaweb.dto;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.debloopers.chibchaweb.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Check that idTicket is present and available when a new Ticket is created.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = TicketIdTicketValid.TicketIdTicketValidValidator.class
)
public @interface TicketIdTicketValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TicketIdTicketValidValidator implements ConstraintValidator<TicketIdTicketValid, String> {

        private final TicketService ticketService;
        private final HttpServletRequest request;

        public TicketIdTicketValidValidator(final TicketService ticketService,
                final HttpServletRequest request) {
            this.ticketService = ticketService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idTicket");
            if (currentId != null) {
                // only relevant for new objects
                return true;
            }
            String error = null;
            if (value == null) {
                // missing input
                error = "NotNull";
            } else if (ticketService.idTicketExists(value)) {
                error = "Exists.ticket.idTicket";
            }
            if (error != null) {
                cvContext.disableDefaultConstraintViolation();
                cvContext.buildConstraintViolationWithTemplate("{" + error + "}")
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

    }

}
