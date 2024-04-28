INSERT INTO theater (name, address_id, hotline, image, state)
VALUES ('Cinestar Quốc Thanh', 1, '028 7300 8881', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/01-Quoc-Thanh-masthead.jpg', 'active'),
       ('Cinestar Hai Bà Trưng (TP.HCM)', 2, '028 7300 7279', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/03-Hai_Ba_Trung_masthead.jpg', 'active'),
       ('Cinestar Sinh Viên (Bình Dương)', 3, '028 7303 8881', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/05_sinh_vien_masthead.jpg', 'active'),
       ('Cinestar Mỹ Tho', 4, '0273 7308 881', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/06_My_Tho_masthead.jpg', 'active'),
       ('Cinestar Kiên Giang', 5, '029 7730 6588', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/07_Kien_Giang_masthead.jpg', 'active'),
       ('Cinestar Lâm Đồng', 6, '028 7300 8881', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/08_Lam_Dong_masthead.jpg', 'active'),
       ('Cinestar Đà Lạt', 7, '0263 3969 969', 'https:\/\/cinestar-api.monamedia.net\/media\/wysiwyg\/CinemaImage\/02-Da_Lat_masthead.jpg', 'active');

INSERT INTO room (name, max_row, max_column, seat_count, available, theater_id)
VALUES ('RAP 1', 5, 5, 25, 1, 3),
       ('RAP 2', 5, 5, 25, 1, 3),
       ('RAP 3', 5, 5, 25, 1, 3),
       ('RAP 5', 5, 5, 25, 1, 3);
