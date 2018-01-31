cat Police_Incidents.tsv | awk -v RS='"[^"]*"' -v ORS= '{gsub(/\n/, " ", RT); print $0  RT}' >incidents_tab_clean.tsv
