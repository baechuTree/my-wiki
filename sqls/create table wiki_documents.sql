create table wiki_documents(
	document_id int unsigned not null auto_increment,
    document_title varchar(255) not null,
    content longtext not null,
    created_at datetime(6) not null default current_timestamp(6),
    updated_at datetime(6) not null default current_timestamp(6)
		on update current_timestamp(6),
        
	constraint pk_wiki_documents primary key (document_id),
    constraint uk_wiki_documents_title unique (document_title)
);

describe wiki_documents;

INSERT INTO wiki_documents(document_title, content) VALUES ("test", "wow");

SELECT * FROM wiki_documents;

DELETE FROM wiki_documents WHERE (document_title = "test");