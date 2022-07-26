INSERT INTO chat.user(name, password) VALUES
('Larry Page','Google'), ('Bill Gates','Microsoft'), ('Mark Zuckerberg','Facebook'),
('Ken Thompson','UNIX'), ('Linus Torvalds','Linux'), ('Ada Lovelace','mathematician')
ON CONFLICT DO NOTHING;

INSERT INTO chat.chatroom(title, owner) VALUES
('Chat 1', (SELECT id FROM chat.user WHERE name = 'Larry Page')),
('Chat 2', (SELECT id FROM chat.user WHERE name = 'Bill Gates')),
('Chat 3', (SELECT id FROM chat.user WHERE name = 'Mark Zuckerberg')),
('Chat 4', (SELECT id FROM chat.user WHERE name = 'Ken Thompson')),
('Chat 5', (SELECT id FROM chat.user WHERE name = 'Linus Torvalds')),
('Chat 6', (SELECT id FROM chat.user WHERE name = 'Ada Lovelace'))
ON CONFLICT DO NOTHING;

INSERT INTO chat.message (author, room, text) VALUES
((SELECT id FROM chat.user WHERE name = 'Larry Page'),      (SELECT id FROM chat.chatroom WHERE title = 'Chat 1'), 'Hello, I am Larry'),
((SELECT id FROM chat.user WHERE name = 'Bill Gates'),      (SELECT id FROM chat.chatroom WHERE title = 'Chat 2'), 'Hello, I am Bill'),
((SELECT id FROM chat.user WHERE name = 'Mark Zuckerberg'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 3'), 'Hello, I am Mark'),
((SELECT id FROM chat.user WHERE name = 'Ken Thompson'),    (SELECT id FROM chat.chatroom WHERE title = 'Chat 4'), 'Hello, I am Ken'),
((SELECT id FROM chat.user WHERE name = 'Linus Torvalds'),  (SELECT id FROM chat.chatroom WHERE title = 'Chat 5'), 'Hello, I am Linus'),
((SELECT id FROM chat.user WHERE name = 'Ada Lovelace'),    (SELECT id FROM chat.chatroom WHERE title = 'Chat 6'), 'Hello, I am Ada')
ON CONFLICT DO NOTHING;

INSERT INTO chat.user_chatroom (user_id, chat_id) VALUES
((SELECT id FROM chat.user WHERE name = 'Larry Page'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 1')),
((SELECT id FROM chat.user WHERE name = 'Bill Gates'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 2')),
((SELECT id FROM chat.user WHERE name = 'Mark Zuckerberg'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 3')),
((SELECT id FROM chat.user WHERE name = 'Ken Thompson'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 4')),
((SELECT id FROM chat.user WHERE name = 'Linus Torvalds'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 5')),
((SELECT id FROM chat.user WHERE name = 'Ada Lovelace'), (SELECT id FROM chat.chatroom WHERE title = 'Chat 6'))
ON CONFLICT DO NOTHING;