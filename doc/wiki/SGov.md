Democropedia - Ontologies - Governmental Structure (US)
=======================================================

## Overview - Scope

Democrapedia shall define a series of ontologies for purpose of
denoting  structural governmental concepts and representative entities
within US federal, US other-regional[1], state, county, and municipal
governmental jurisdictions. That set of ontologies shall be referred
to as the US Structured Government (SGov) ontologies.

## Definitive Works

The first prototype of the SGov ontologies was developed using a UML
model, in an informal analysis of personal, common knowledge with
regards to governmental structure and structural relations in the US.

### Definitive Works in SGov Prototype 1

In the intial prototype of the SGov ontologies, it has been a primary
goal to illustrate that the balanced federal "Three branches" model is
reified within more localized jurisdictions, so local as the
municipality having a city council (essentially fulfilling a
legislative role), a mayor's office, a police department (whether
formally as an executive department or as an extension of regional
judicial office), any executive departments managed under the mayor's
office or other municipal agency, and municipal and/or county courts.

A secondary goal in the design of SGov Prototype 1 has been to begin
to illustrate classes of institutions and other entities (e.g region)
and entity relations, for purpose of defining an edition of SGov
Prototype 1 in modular OWL ontologies, namely

1. Federal Module
2. for each federally recognized indigenous tribe (cf. National Atlas)
   and each outlying region (cf. FIPS codes?) one region-specific
   module
3. for each state, one state-specific module
4. for each county in each state (cf. National Atlas) one
   county-specific module (Fixme: Are there analogous structures
   within tribes and outlying regions?)
5. for each municipality in each county (cf. National Atlas and
   DE-9IM) one municpality-specific module
6. to be accompanied with procedural documentation and formal tool
   application models, for re-application after changes in initial
   models (cf. National Atlas, Geotools, Apache Ant)

### Towards Later Revisions of the SGov Ontologies

Once the federal module for SGov Prototype 1 may be defined in OWL
format, the SGov federal ontology may be extended as onto coverage of
congressional bills and bill process, executive departments and their
formal publications, and judicial activity in the SCOTUS.

The remaining SGov modular ontologies, for purpose of brevity, maynbe
referenced singularly onto Wikipedia.

It is a goal of the Democrapedia project: To develop a discrete
refernce chain within all Democrapedia ontologies. Democrapedia shall
seek formal, legally public, primary works of reference in developing
the SGov ontologies, and shall denote those works of reference within
all derived assertions, within the SGov ontologies.

## Documentation

The SGov ontologies shall be described with UML class diagrams and OWL
entity metadata.

(To Do: This may be accompanied with the development of an ODM OWL
module for Modelio, and/or an ODM OWL module for Eclipse Papyrus)

## Use Cases

### Overview of Congressional Comittees and Comittee Actions

If the collected works of Congress would be viewed only at the level
of individual bills entered into the Congresional process, it may
present simply an overwhelming "lot of text." When, rather, the works
of Congress would be viewed as in a context of actions within
individual Congressional committees - including actions directly with
regards to legislative bills and other committee activities - then the
Congressional process and the Congressional record may be more
apparent for its topically and conceptually relevant qualities.

(Maybe it could be viewed as like an "Adopt a Committee" program,
although Congress might be nonplussed by such a concept)

### Overview of State Legislative Comittees and Comittee Actions

(See also: The federal model)

### Overview of Judicial Appointment

(Courts do work, too)

### Structured Documentation of Federal Institutions and Institutional Relations

(...)

### Citizen "Watchdog" View of Public Records Describing Works of Lobbying Towards Legislative, Judicial, and Executive Offices

(as far as lobbying towards judicial offices: Citation needed)

### Educational Roles of SGov Structured Ontology Views

(cf. School - where one may begin to learn how the government is
structured, if not also why the government's structure is relevant, as
with regards to matters of state - perhaps also, _why Congress was
designed to run slowly_,  like as to prevent transitory revolutions of
partisan agenda)

## Implementation

### Data Space

The structure of the SGov data space shall resemble the structure of
the UML package names as defined in the original
[SGov ontology draft][sgovDraft], ammended with the following
specification:

* The `sgov::ontology` package shall represent the baseline hostname for
  the containing Democrapedia project, suffixed with the pathname
  `data/owl/`.

* The name of each package contained within `sgov::ontology` shall
  identify a pathname component of type _filesystem directory_ or
  _WebDAV collection_ within `data/owl/` (Essentially, this represents
  a mapping of the _UML Package_ type to either _filesystem directory_
  or _WebDAV collection_)


#### Regional Reference Ontologies


**Core Ontologies**

Democrapedia shall define a set of regional core ontologies, those
defining such ontology classes as shall be utilized within reigonal
instance ontologies. The set of regional core ontologies is denoted in
the following list, with corresponding UML package names.

* `sgov::ontology::regional::us::state::stateCore` [OWLOntology]
    * defines OWL class `State` and related core properties

* `sgov::ontology::regional::us::state::county::countyCore` [OWLOntology]
    * defines OWL class `County` and related core properties

* `sgov::ontology::regional::us::oa::oaCore` [OWLOntology]
    * defines OWL class `OutlyingArea` and related core properties

* `sgov::ontology::regional::us::metro::metroCore` [OWLOntology]
    * defines OWL class `MetroArea` and related core properties


**Instance Composite Reference Ontologies**

* `sgov::ontology::regional::us::state::statesAreas` [OWLOntolgy]
    * Composite ontology for definitions of all states' and outlying
      areas' regional instance feature data

* `sgov::ontology::regional::us::state::statesUS` [OWLOntology]
    * Composite ontology for definitions of all states' regional
      instance feature data

* `sgov::ontology::regional::us::oa:oaUS`  [OWLOntology]
    * Composite ontology for definitions of all outlying areas'
      regional instance feature data

**Instance Reference Ontologies - States, Counties**

* `sgov::ontology::regional::us::state::state<CC>` [Name Pattern]
    * Let `<CC>` represent the two-letter GNIS name code for the state
    * This name pattern denotes the name pattern for each state ontology
    * Each `state<CC>` ontology shall import the `stateCore` ontology

* `sgov::ontology::regional::us::state::counties<CC>` [Name Pattern]
    * Let `<CC>` represent the two-letter GNIS name code for the state
    * This name pattern denotes the name pattern for the county list
      ontology for each state
    * Each `county<CC>` ontology shall import the `countyCore` ontology


**Instance Reference Ontologies - Outlying Areas**

(**TO DO**)

* `sgov::ontology::regional::us::oa:oa<CC>`
* `sgov::ontology::regional::us::oa:countyEquiv<CC>`

**TO DO**

The SGov ontology shall define reference ontology structures and
reference ontology name patterns for the following entities

* Information as to the date when each region was established
* Types of outlying area
* Types of _county equivalent_ regions in outlying areas
* Types of _indigenous regions_ in the US (Alaska; Reservations)
* Instances of _indigenous regions_ in the US (cf. [TIGER][tiger], [National Atlas][natlAtlas])
* Metro Areas (census data, economic data, cf. [TIGER][tiger])


## Footnotes

[1] Those "Other regions" shall include: sovereign indigenous nations
and outlying areas such as Guam and Philippines. Care shall be taken
to not relegate those "other regions'" governments to the nature of a
US State region, but rather to denote those regions' governments for
their regional, institutional, and principled sovereignty.

[sgovDraft]: https://github.com/GazeboHub/Democrapedia/raw/master/doc/cubetto/Democrapedia.pdf
[tiger]: http://www.census.gov/geo/maps-data/data/tiger.html
[natlAtlas]: http://nationalatlas.gov/maplayers.html
