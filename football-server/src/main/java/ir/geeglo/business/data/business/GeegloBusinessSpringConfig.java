package ir.geeglo.business.data.business;

import ir.geeglo.business.data.service.GeegloServiceSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * Created by SYSTEM on 8/13/2017.
 */
@Configuration
@ComponentScan
@Import(GeegloServiceSpringConfig.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GeegloBusinessSpringConfig {
}
