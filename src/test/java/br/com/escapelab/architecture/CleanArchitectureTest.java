package br.com.escapelab.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
        packages = "br.com.escapelab",
        importOptions = ImportOption.DoNotIncludeTests.class)
class CleanArchitectureTest {

    @ArchTest
    static final ArchRule domainDependsOnlyOnItselfAndTheJdk = classes()
            .that().resideInAPackage("..domain..")
            .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "java..",
                    "br.com.escapelab.domain..");

    @ArchTest
    static final ArchRule applicationDependsOnlyOnDomainAndTheJdk = classes()
            .that().resideInAPackage("..application..")
            .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "java..",
                    "br.com.escapelab.application..",
                    "br.com.escapelab.domain..");

    @ArchTest
    static final ArchRule configurationIsFrameworkAgnostic = classes()
            .that().resideInAPackage("..configuration..")
            .should().onlyDependOnClassesThat().resideInAnyPackage(
                    "java..",
                    "br.com.escapelab.configuration..");

    @ArchTest
    static final ArchRule javaFxStaysInPresentation = noClasses()
            .that().resideOutsideOfPackage("..presentation.javafx..")
            .should().dependOnClassesThat().resideInAPackage("javafx..");

    @ArchTest
    static final ArchRule topLevelPackagesAreFreeOfCycles = slices()
            .matching("br.com.escapelab.(*)..")
            .should().beFreeOfCycles();
}
