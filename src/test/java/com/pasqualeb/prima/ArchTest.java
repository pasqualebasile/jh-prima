package com.pasqualeb.prima;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.pasqualeb.prima");

        noClasses()
            .that()
            .resideInAnyPackage("com.pasqualeb.prima.service..")
            .or()
            .resideInAnyPackage("com.pasqualeb.prima.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.pasqualeb.prima.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
