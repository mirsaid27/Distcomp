insert into "user" (id, login, password, first_name, last_name)
values (1, 'a', '0', 'test', 'testset'),
       (2, 'b', '2', 'test2', 'testestset');
-- update sequence to hibernate can proceed proper work
alter sequence "user_seq" restart with 4;

insert into "tweets" (id, title, content, created, modified, user_id)
values (1, 'Test title',
        'Test content',
        current_timestamp, current_timestamp, 1);
alter sequence "tweets_seq" restart with 2;

insert into "stickers" (id, name)
values (1, 'Economics'),
       (2, 'Politics'),
       (3, 'Science'),
       (4, 'Elon Musk');
alter sequence "label_seq" restart with 5;


insert into "comments" (id, content, news_id)
values (1, 'Lol', 1),
       (2, 'xdd', 1);
alter sequence "comment_seq" restart with 3;

-- many-to-many relation ship with composite key
insert into "tweets_sticker" (tweet_id, sticker_id)
values (1, 4);