
--Load the Dallas police incidents data the zip code population data

incident_file = LOAD 'incidents/incidents_2017.tsv' USING PigStorage();
       
zippop = LOAD 'incidents/zip_totpop.csv' USING PigStorage(',') as (popzip:chararray, totpop:int);

    
incidents = foreach incident_file generate 
					(chararray) $0 as incidnumwithyear, 
					(chararray) $1 as year, 
					(chararray) $2 as incidnum,
					(chararray) $3 as offsvcnum, 
					(chararray) $4 as svcnumid,
					(chararray) $5 as watch,

					(chararray) $16 as zip;

--Use limit to view the first 5 
incidents
c = limit incidents 5;
dump c;

--Use foreach and then distinct to print a list of the distinct watches

d = foreach incidents generate watch;

distinctwatch = distinct d;

DUMP distinctwatch;

--Use left outer join to get the population for each zip code and population

incident_pop = join incidents by zip left outer, zippop by popzip;

incidentwithpop = foreach incident_pop generate incidnum, popzip, totpop;

incident5 = limit incidentwithpop 5;

dump incident5; -- Print 5 incidents with their zip code and population


--Print out zip codes that didn't have a population in the population file

incident_nopop = filter incidentwithpop by totpop is null;

g = foreach incident_nopop generate popzip;

h = distinct g;

dump h;
