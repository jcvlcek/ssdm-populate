USE [SSDI]
GO

/****** Object:  Table [dbo].[RECORDS]    Script Date: 04/01/2014 21:29:21 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[RECORDS](
	[SSAN] [int] NOT NULL,
	[LASTNAME] [varchar](50) NOT NULL,
	[FIRSTNAME] [varchar](50) NOT NULL,
	[MIDDLENAME] [varchar](50) NOT NULL,
 CONSTRAINT [PK_RECORDS] PRIMARY KEY CLUSTERED 
(
	[SSAN] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
