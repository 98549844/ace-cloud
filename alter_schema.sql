

create schema ace_module default character set utf8mb4 collate utf8mb4_unicode_ci;#创建数据库
grant select,insert,update,delete,create on ace_module.* to root;#用户授权数据库
flush privileges;#立即启用配置

create schema ace_client default character set utf8mb4 collate utf8mb4_unicode_ci;#创建数据库
grant select,insert,update,delete,create on ace_client.* to root;#用户授权数据库
flush privileges;#立即启用配置

create schema ace_gateway default character set utf8mb4 collate utf8mb4_unicode_ci;#创建数据库
grant select,insert,update,delete,create on ace_gateway.* to root;#用户授权数据库
flush privileges;#立即启用配置


grant select,insert,update,delete,create on ace_module.* to garlam;#用户授权数据库
commit;
drop schema ace_module;#删除数据库

