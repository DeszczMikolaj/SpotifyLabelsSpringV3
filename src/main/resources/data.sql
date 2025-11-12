INSERT INTO labels (name) VALUES
  ('happy'),
  ('sad'),
  ('energetic');

INSERT INTO tracks (spotify_id, name, artists) VALUES
    ('SPOTIFY_ID_1', 'Track 1', 'Artist 1, Artist 2, Artist 3'),
    ('SPOTIFY_ID_2', 'Track 2', 'Artist 2'),
    ('SPOTIFY_ID_3', 'Track 3', 'Artist 3');

INSERT INTO labels_tracks (label_id, track_id) VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,2),
    (2,3),
    (3,3);