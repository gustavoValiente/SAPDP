/****** 
	Script de criação de dados iniciais da aplicação SAPDP
	Dados necessários para carregamento inicial da aplicação SAPDP
******/
USE BD_SAPDP;
/**Inserindo dados default na tabela TB_REGIONAL**/
INSERT INTO BD_SAPDP.dbo.TB_REGIONAL(nome) values('REGIONAL DEFAULT');

/**Inserindo dados default na tabela TB_ESTADO**/
INSERT INTO BD_SAPDP.dbo.TB_ESTADO(nome,sigla) values('ESTADO DEFAULT','ED');

/**Inserindo dados default na tabela TB_MUNICIPIO_DISTRITO**/
INSERT INTO BD_SAPDP.dbo.TB_MUNICIPIO_DISTRITO(nome,id_estado,id_regional) values('MUNICIPIO DEFAULT',1,1);

/**Inserindo dados default na tabela TB_DEFENSORIA**/
INSERT INTO BD_SAPDP.dbo.TB_DEFENSORIA(nome,id_comarca,tipo) values('DEFENSORIA DEFAULT',1,'PRIMEIRA_INSTANCIA');

/**Inserindo dados default na tabela TB_NUCLEO**/
INSERT INTO BD_SAPDP.dbo.TB_NUCLEO(nome,area) values('NUCLEO DEFAULT','CIVEL');

/**Inserindo dados default na tabela TB_NUCLEOS_DEFENSORIAS**/
INSERT INTO BD_SAPDP.dbo.TB_NUCLEOS_DEFENSORIAS(id_defensoria,id_nucleo) values(1,1);

/**Inserindo dados default na tabela TB_UNIDADE**/
INSERT INTO BD_SAPDP.dbo.TB_UNIDADE(nome,id_municipio,descricao) values('UNIDADE DEFAULT',1,'');


/**Inserindo USUARIOS**/
INSERT INTO BD_SAPDP.dbo.TB_USUARIO(id_unidade,login,nome,senha,status, dataCadastro)
	 VALUES(1,'master','master','master',1,GETDATE());

/**Inserindo USUARIOS**/
INSERT INTO BD_SAPDP.dbo.TB_GRUPOS_USUARIOS(groupname,login) VALUES('MASTER','master');








