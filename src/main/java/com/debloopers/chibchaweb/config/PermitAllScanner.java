package com.debloopers.chibchaweb.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class PermitAllScanner {

    private final ApplicationContext applicationContext;

    public PermitAllScanner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean(name = "permitAllEndpoints")
    public Set<String> scanPermitAllEndpoints() {
        Set<String> permitAllPaths = new HashSet<>();

        String[] beanNames = applicationContext.getBeanNamesForAnnotation(RestController.class);

        for (String beanName : beanNames) {
            Object controllerBean = applicationContext.getBean(beanName);
            Class<?> controllerClass = controllerBean.getClass();

            String[] basePaths = new String[]{""};
            RequestMapping classMapping = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RequestMapping.class);
            if (classMapping != null && classMapping.value().length > 0) {
                basePaths = classMapping.value();
            }

            for (Method method : controllerClass.getDeclaredMethods()) {
                PreAuthorize preAuth = AnnotatedElementUtils.findMergedAnnotation(method, PreAuthorize.class);
                if (preAuth != null && preAuth.value().trim().equals("permitAll()")) {

                    Set<String> methodPaths = getPathFromMethod(method);
                    for (String base : basePaths) {
                        for (String methodPath : methodPaths) {
                            String fullPath = (base + "/" + methodPath).replaceAll("//+", "/");
                            permitAllPaths.add(fullPath);
                        }
                    }
                }
            }
        }

        return permitAllPaths;
    }

    private Set<String> getPathFromMethod(Method method) {
        Set<String> paths = new HashSet<>();

        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping get = method.getAnnotation(GetMapping.class);
            addPaths(paths, get.value());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping post = method.getAnnotation(PostMapping.class);
            addPaths(paths, post.value());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping put = method.getAnnotation(PutMapping.class);
            addPaths(paths, put.value());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping delete = method.getAnnotation(DeleteMapping.class);
            addPaths(paths, delete.value());
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping request = method.getAnnotation(RequestMapping.class);
            addPaths(paths, request.value());
        }

        return paths;
    }

    private void addPaths(Set<String> paths, String[] values) {
        if (values.length == 0) {
            paths.add("");
        } else {
            for (String v : values) {
                paths.add(v.startsWith("/") ? v : "/" + v);
            }
        }
    }
}