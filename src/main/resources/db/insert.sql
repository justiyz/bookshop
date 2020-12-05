SET FOREIGN_KEY_CHECKS = 0;

truncate table book;
truncate table store;


INSERT into store(`id`, `name`, `location`, `contact_no`)
VALUES(10, 'Macmillian', 'Lagos', '09011223310'),
        (20, 'Johnson Books', 'Benin', '0901122331'),
        (30, 'Hugo Bookshop', 'Lagos', '090112233'),
        (40, 'Elite Bookshop', 'Jos', '09011223'),
        (50, 'Lantern', 'Rivers', '0901122');


INSERT into book(`id`, `title`, `author`, `genre`, `year`, `store_id`)
VALUES(1, 'Snow Crash', 'Neal Stephenson', 'Sci-Fi', '1970', 10),
        (2, 'Game of Thrones', 'George R.R. Martin', 'Fantasy', '2000', 10),
        (3, 'Sapiens', 'Yuval Noah', 'History', '1980', 10),
        (4, 'Things Fall Apart', 'Chinua Achebe', 'Suspense and Thrillers', '1980', 20),
        (5, 'Chike and the river', 'Chinua Achebe', 'Short Stories', '1966', 20),
        (6, 'The Moon Stone', 'Wilkie Collins', 'Detective & Mystery', '1958', 20);


SET FOREIGN_KEY_CHECKS = 1;