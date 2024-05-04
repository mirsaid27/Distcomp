INSERT INTO tbl_user (id, firstname, lastname, login, password)
VALUES (10, 'Artyom', 'Belsky', 'rebel', 'rebelpw'),
       (20, 'Aleksandr', 'Khmelov', 'khmelov_a', 'khmelov_password');

insert into tbl_issue (id, user_id, title, content, created, modified)
values (10, 10, 'Title 1', 'Content 1', '2007-12-03T09:15:30Z', '2008-12-03T09:15:30Z'),
       (20, 20, 'Title 2', 'Content 2', '2007-12-03T09:15:30Z', '2008-12-03T09:15:30Z');

insert into tbl_label (id, `name`)
values (10, 'Label 1'),
       (20, 'Label 2');

insert into tbl_issue_label (issue_id, label_id)
values (10, 10),
       (20, 20);