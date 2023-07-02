package com.agroniks.marketplace;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

@AnalyzeClasses(packages = "com.agroniks.marketplace", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ItemPersistenceArchTest {



    @ArchTest
    public void setFieldsInEntityShouldNotBePublic(JavaClasses jc) {
        fields().that().areDeclaredInClassesThat().areAnnotatedWith(Entity.class).should().bePrivate().check(jc);
    }

    @ArchTest
    public void setEntitiesShouldBePublic(JavaClasses jc) {
        classes().that()
                .areAnnotatedWith(Entity.class)
                .should().bePublic()
                .andShould().beAnnotatedWith(Table.class)
                .because("I said so").check(jc);
    }

    @ArchTest
    public void setServicesShouldOnlyAccessToRepositories(JavaClasses jc) {
        classes().that().areAnnotatedWith(Service.class)
                .should().accessClassesThat().areAnnotatedWith(Repository.class).check(jc);
    }

    @ArchTest
    public void setControllerShouldPerformActionsViaServicesNotRepositories(JavaClasses jc) {
        classes().that().areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class)
                .should().accessClassesThat().areAnnotatedWith(Service.class)
                .check(jc);
    }

}

