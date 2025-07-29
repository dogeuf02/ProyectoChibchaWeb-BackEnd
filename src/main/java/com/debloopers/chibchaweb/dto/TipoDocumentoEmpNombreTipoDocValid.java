package com.debloopers.chibchaweb.dto;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.debloopers.chibchaweb.service.TipoDocumentoEmpService;
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
 * Check that nombreTipoDoc is present and available when a new TipoDocumentoEmp is created.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = TipoDocumentoEmpNombreTipoDocValid.TipoDocumentoEmpNombreTipoDocValidValidator.class
)
public @interface TipoDocumentoEmpNombreTipoDocValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TipoDocumentoEmpNombreTipoDocValidValidator implements ConstraintValidator<TipoDocumentoEmpNombreTipoDocValid, String> {

        private final TipoDocumentoEmpService tipoDocumentoEmpService;
        private final HttpServletRequest request;

        public TipoDocumentoEmpNombreTipoDocValidValidator(
                final TipoDocumentoEmpService tipoDocumentoEmpService,
                final HttpServletRequest request) {
            this.tipoDocumentoEmpService = tipoDocumentoEmpService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("nombreTipoDoc");
            if (currentId != null) {
                // only relevant for new objects
                return true;
            }
            String error = null;
            if (value == null) {
                // missing input
                error = "NotNull";
            } else if (tipoDocumentoEmpService.nombreTipoDocExists(value)) {
                error = "Exists.tipoDocumentoEmp.nombreTipoDoc";
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
