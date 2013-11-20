Catalog Manager - Brontes Suite
===============================

The goal of the Brontes Catalog Manager is to provide a framework for loading ontologies as distributed within Maven repositry artifacts.

## Specification (Non-Normative)

### Overview

**Inputs:** Ontology URI, OntModelSpec
**Configuration Parameter:** Catalog of URI onto Maven artifacts 
**Goal:** Load model from within Maven artifact

### Components

* "x-mvn" filesystem implementation (Commons VFS)
    * Integrate with Shrinkwrap
    
* Configuration reader for ontology mapping files (XML)
    * Develop a schema; Use JAXB

* Ontology reader integration
    * Provide Jena FileManager extension using ontology mapping file reader


### XML Catalog Integration for Maven Artifacts

This implementation shall extend XML catalogs with an unofficial URI scheme "x-mvn"

e.g URI syntax

    x-mvn:com.example:pizza-km:1.0:jar/META-INF/pizza.rdf

The resolver for "x-mvn" URI may be implemented via an intermediary "x-mvn" handler for Apache CommonsVFS, implementing a new _filesystem type_ onto the latter.


### Ontology Reader Configuration

#### Ontology Reader Configuration Syntax

(...)

#### Ontology Import - Semantics Onto Maven Repository Integration

(...)

#### `OntModelSpec` Selection

In addition to an on ontology URI, the ontology loader in this implementation must allow for specification of an `OntModelSpec` to be used when loading a specified ontology
