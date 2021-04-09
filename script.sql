/*==============================================================*/
/* Drop Indexes                                                 */
/*==============================================================*/
DROP INDEX Etudiant_Index;
DROP INDEX Enseignant_Index;

/*==============================================================*/
/* Drop Views							                                      */
/*==============================================================*/
DROP VIEW MoyEtuMat;
DROP VIEW MgEtu;

/*==============================================================*/
/* Drop Tables                                                  */
/*==============================================================*/
DROP TABLE EtudiantUnite cascade constraints;
DROP TABLE Unite cascade constraints;
DROP TABLE Enseignant cascade constraints;
DROP TABLE Etudiant cascade constraints;


/*==============================================================*/
/* Table: Etudiant                                              */
/*==============================================================*/
CREATE TABLE Etudiant(
matricule_etu int CHECK(matricule_etu>=20190000 and matricule_etu<=20199999),
nom_etu varchar2(20) not null,
prenom_etu varchar2(25) not null,
date_naissance varchar2(10) not null,
adresse varchar2(100) not null,
constraint PK_Etudiant primary key (matricule_etu)
) ;

/*==============================================================*/
/* Table: Enseignant                                            */
/*==============================================================*/
CREATE TABLE Enseignant(
matricule_ens int,
nom_ens varchar2(20) not null,
prenom_ens varchar2(20) not null,
constraint PK_Enseignant primary key (matricule_ens)
) ;

/*==============================================================*/
/* Table: Unite                                                 */
/*==============================================================*/
CREATE TABLE Unite(
code_Unite varchar2(7) not null,
libelle varchar2(10) not null,
nbr_heures int,
matricule_ens int,
constraint PK_Unite primary key (code_Unite),
constraint FK_Unite_Matricule foreign key (matricule_ens)
           references Enseignant (matricule_ens)
) ;

/*==============================================================*/
/* Table: EtudiantUnite                                         */
/*==============================================================*/
CREATE TABLE EtudiantUnite
(matricule_etu int not null,
code_Unite varchar2(7) not null,
note_CC int,
note_TP int,
note_examen int,
constraint PK_Etudiant_Unite primary key (matricule_etu, code_Unite),
constraint FK_Etudiant_Unite_Matricule foreign key (matricule_etu)
           references Etudiant (matricule_etu),
constraint FK_Etudiant_Unite_Code foreign key (code_Unite)
           references Unite (code_Unite)
) ;


/*==============================================================*/
/* VIEW: MoyEtuMat						                                  */
/*==============================================================*/
CREATE VIEW MoyEtuMat AS
SELECT E.matricule_etu as matricule_etu, U.libelle AS libelle, AVG(EU.note_CC + EU.note_TP + EU.note_examen)/3.0 AS MoyEtuMat
FROM Etudiant E, Unite U, EtudiantUnite EU
WHERE EU.code_Unite = U.code_unite
AND EU.matricule_etu = E.matricule_etu
GROUP BY E.matricule_etu, U.libelle;

/*==============================================================*/
/* VIEW: MgEtu							                                    */
/*==============================================================*/
CREATE VIEW MgEtu AS
SELECT M.matricule_etu as matricule_etu, SUM(M.MoyEtuMat)/(SELECT COUNT(*)
FROM EtudiantUnite EU
WHERE M.matricule_etu = EU.matricule_etu) AS MgEtu
FROM MoyEtuMat M, NbrUnite N
WHERE M.matricule_etu = N.matricule_etu
GROUP BY M.matricule_etu ;

/*==============================================================*/
/* VIEW: NbrUnite							                            */
/*==============================================================*/
CREATE VIEW NbrUnite AS
SELECT E.matricule_etu as matricule_etu, COUNT(*) as total
FROM Etudiant E, EtudiantUnite EU
WHERE E.matricule_etu = EU.matricule_etu
GROUP BY E.matricule_etu;

/*==============================================================*/
/* Indexes:                                                     */
/*==============================================================*/
CREATE  INDEX Etudiant_Index on Etudiant (
   nom_etu ASC
) ;

CREATE  INDEX Enseignant_Index on Enseignant (
   nom_ens DESC
) ;

/*==============================================================*/
/* DATA:							                                          */
/*==============================================================*/
INSERT INTO Etudiant VALUES(20190001, 'BOUSSAI', 'MOHAMED', '12/01/2000', 'Alger');
INSERT INTO Etudiant VALUES(20190002, 'CHAID', 'LAMIA', '01/10/1999', 'Batna');
INSERT INTO Etudiant VALUES(20190003, 'BRAHIMI', 'SOUAD', '18/11/2000', 'Setif');
INSERT INTO Etudiant VALUES(20190004, 'MEHAMLI', 'LILIA', '19/02/2001', 'Alger');
INSERT INTO Etudiant VALUES(20190005, 'HAMLAOUI', 'RACHID', '23/05/1998', 'Oran');
INSERT INTO Etudiant VALUES(20190006, 'LAMA', 'SAID', '23/05/1999', 'Tizi Ouzou');
INSERT INTO Etudiant VALUES(20190007, 'GHAYEB', 'IMAD', '31/01/1998', 'Djelfa');
INSERT INTO Etudiant VALUES(20190008, 'SAIF', 'SOHEIB', '23/08/1998', 'Tamenraset');
INSERT INTO Etudiant VALUES(20190009, 'KARTAF', 'OMAR', '02/07/2001', 'Constantine');
INSERT INTO Etudiant VALUES(20190010, 'HARACH', 'GHILES', '15/03/2000', 'Anaba');
INSERT INTO Etudiant VALUES(20190011, 'LARABI', 'AYMEN', '20/01/2000', 'Alger');
INSERT INTO Etudiant VALUES(20190012, 'DIRAOUI', 'RAOUF', '21/02/2000', 'Alger');
INSERT INTO Etudiant VALUES(20190013, 'CHAYEB', 'MOURAD', '04/07/2001', 'Batna');
INSERT INTO Etudiant VALUES(20190014, 'BOUSSEHAL', 'YASMINE', '07/11/1999', 'Blida');
INSERT INTO Etudiant VALUES(20190015, 'MEKHLOUF', 'LAMIA', '12/12/2000', 'Saida');
INSERT INTO Etudiant VALUES(20190016, 'MOUSSEF', 'SARAH', '12/04/2001', 'Blida');
INSERT INTO Etudiant VALUES(20190017, 'CHRIF', 'YOUSSEF', '03/06/1998', 'Setif');
INSERT INTO Etudiant VALUES(20190018, 'KILANI', 'YANIS', '30/01/2000', 'Alger');

INSERT INTO Enseignant VALUES(20000001, 'HAROUNI', 'AMINE');
INSERT INTO Enseignant VALUES(19990011, 'FATHI', 'OMAR');
INSERT INTO Enseignant VALUES(19980078, 'BOUZIDANE', 'FARAH');
INSERT INTO Enseignant VALUES(20170018, 'ARABI', 'ZOUBIDA');
INSERT INTO Enseignant VALUES(19980079, 'KECHROUD', 'YANIS');
INSERT INTO Enseignant VALUES(20110085, 'DJEBIEN', 'HIND');
INSERT INTO Enseignant VALUES(10870302, 'RIGHI', 'FAYCAL');

INSERT INTO Unite VALUES('FEI0001', 'POO', 6, 20000001);
INSERT INTO Unite VALUES('FEI0002', 'BDD', 6, 19990011);
INSERT INTO Unite VALUES('FEI0003', 'THL', 3, 19980078);
INSERT INTO Unite VALUES('FEI0004', 'SYSTEME', 4, 20170018);
INSERT INTO Unite VALUES('FEI0005', 'ANGLAIS', 2, 19980079);
INSERT INTO Unite VALUES('FEI0006', 'ARCHI', 4, 20110085);
INSERT INTO Unite VALUES('FEI0007', 'PYTHON', 2, 10870302);

INSERT INTO EtudiantUnite VALUES(20190001, 'FEI0001', 10, 15, 9);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0001', 20, 13, 10);
INSERT INTO EtudiantUnite VALUES(20190004, 'FEI0001', 13, 17, 16);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0002', 10, 16, 17);
INSERT INTO EtudiantUnite VALUES(20190003, 'FEI0002', 9, 8, 15);
INSERT INTO EtudiantUnite VALUES(20190004, 'FEI0002', 15, 9, 20);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0003', 14, 16, 13);
INSERT INTO EtudiantUnite VALUES(20190006, 'FEI0003', 18, 16, 15);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0004', 12, 18, 14);
INSERT INTO EtudiantUnite VALUES(20190003, 'FEI0004', 17, 12, 15);
INSERT INTO EtudiantUnite VALUES(20190004, 'FEI0004', 12, 13, 20);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0005', 15, 12, 09);
INSERT INTO EtudiantUnite VALUES(20190018, 'FEI0005', 09, 9, 08);
INSERT INTO EtudiantUnite VALUES(20190010, 'FEI0005', 11, 9, 08);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0006', 15, 12, 11);
INSERT INTO EtudiantUnite VALUES(20190012, 'FEI0006', 17, 13, 12);
INSERT INTO EtudiantUnite VALUES(20190011, 'FEI0006', 17, 11, 17);
INSERT INTO EtudiantUnite VALUES(20190013, 'FEI0006', 09, 12, 18);
INSERT INTO EtudiantUnite VALUES(20190004, 'FEI0006', 11, 15, 16);
INSERT INTO EtudiantUnite VALUES(20190005, 'FEI0006', 12, 11, 13);
INSERT INTO EtudiantUnite VALUES(20190002, 'FEI0007', 17, 15, 14);
INSERT INTO EtudiantUnite VALUES(20190008, 'FEI0007', 17, 16, 15);
INSERT INTO EtudiantUnite VALUES(20190009, 'FEI0007', 19, 16, 13);
INSERT INTO EtudiantUnite VALUES(20190015, 'FEI0007', 20, 18, 19);

COMMIT;
