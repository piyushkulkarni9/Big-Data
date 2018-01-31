sqoop import \
--connect jdbc:mysql://lakers.psekhosting.com/austinh_buandb \
--driver com.mysql.jdbc.Driver \
--username austinh_buan \
--password z96cp2fc95 \
--table tbl4flume2 \
--where "wages > 40000" \
-m 1 \
--target-dir /user/root/sqoop3 
