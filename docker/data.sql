INSERT INTO public.organizations
VALUES ('b7f120ec-23d9-4a11-a49a-1dd94e18c8d1', 'TechSolutions', 'John Smith', 'john@techsolutions.com', '555-1234'),
       ('98a1b3ab-e447-4859-9b1b-95f216e0e98b', 'InnovateNow', 'Jane Doe', 'jane@innovatenow.com', '555-5678'),
       ('d6b37ee6-44a2-44e6-af21-06cbe6e5e80f', 'SwiftBuilders', 'David Lee', 'david@swiftbuilders.com', '555-9876'),
       ('09af8d2b-225a-4e34-a156-037ac9cb1684', 'BrightMinds', 'Samantha Chen', 'samantha@brightminds.com', '555-5555'),
       ('fc2cbf31-8692-4f6b-aee4-7d9b4f59bbaf', 'PixelWorks', 'Michael Johnson', 'michael@pixelworks.com', '555-2468');

INSERT INTO public.licenses
VALUES ('2e7c6b19-91b7-47ab-aae2-cba6c5fb17cb', 'b7f120ec-23d9-4a11-a49a-1dd94e18c8d1', 'Mobile App', 'TechSolutions',
        'limited', 'App Dev 101'),
       ('c66e5189-51d9-4423-a99b-2c8e7bbf558c', '98a1b3ab-e447-4859-9b1b-95f216e0e98b', 'CRM Software', 'InnovateNow',
        'complete', 'SalesPro'),
       ('d5b5e5e6-2db6-4a6a-b4cb-4d9c4fa07d8d', 'd6b37ee6-44a2-44e6-af21-06cbe6e5e80f', 'Project Management Tool',
        'SwiftBuilders', 'limited', 'ProjectMate'),
       ('efd048d6-8e14-4dc6-aef7-52a2e90d7d3a', '09af8d2b-225a-4e34-a156-037ac9cb1684', 'Video Editing Software',
        'BrightMinds', 'complete', 'VideoPro'),
       ('7b3856e8-6dbd-4f94-b4e4-791d17968854', 'fc2cbf31-8692-4f6b-aee4-7d9b4f59bbaf', 'Image Editing Software',
        'PixelWorks', 'limited', 'PixelMate');