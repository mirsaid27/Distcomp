using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace Publisher.Migrations
{
    /// <inheritdoc />
    public partial class initial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tbl_sticker",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_sticker", x => x.id);
                    table.CheckConstraint("len(name) < 2", "LENGTH(\"name\") > 2");
                });

            migrationBuilder.CreateTable(
                name: "tbl_user",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    login = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    password = table.Column<string>(type: "character varying(128)", maxLength: 128, nullable: false),
                    firstname = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    lastname = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_user", x => x.id);
                    table.CheckConstraint("len(firstname) < 2", "LENGTH(\"firstname\") > 2");
                    table.CheckConstraint("len(lastname) < 2", "LENGTH(\"lastname\") > 2");
                    table.CheckConstraint("len(login) < 2", "LENGTH(\"login\") > 2");
                    table.CheckConstraint("len(password) < 8", "LENGTH(\"password\") > 8");
                });

            migrationBuilder.CreateTable(
                name: "tbl_tweet",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    title = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    created = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    userId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_tweet", x => x.id);
                    table.CheckConstraint("len(content) < 4", "LENGTH(\"content\") > 4");
                    table.CheckConstraint("len(title) < 2", "LENGTH(\"title\") > 2");
                    table.ForeignKey(
                        name: "FK_tbl_tweet_tbl_user_userId",
                        column: x => x.userId,
                        principalTable: "tbl_user",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_comment",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    tweetId = table.Column<int>(type: "integer", nullable: false),
                    content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_comment", x => x.id);
                    table.CheckConstraint("len(content) < 2", "LENGTH(\"content\") > 2");
                    table.ForeignKey(
                        name: "FK_tbl_comment_tbl_tweet_tweetId",
                        column: x => x.tweetId,
                        principalTable: "tbl_tweet",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_tweetSticker",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    tweetId = table.Column<int>(type: "integer", nullable: false),
                    stickerId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_tweetSticker", x => x.id);
                    table.ForeignKey(
                        name: "FK_tbl_tweetSticker_tbl_sticker_stickerId",
                        column: x => x.stickerId,
                        principalTable: "tbl_sticker",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_tbl_tweetSticker_tbl_tweet_tweetId",
                        column: x => x.tweetId,
                        principalTable: "tbl_tweet",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_tbl_comment_tweetId",
                table: "tbl_comment",
                column: "tweetId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_sticker_name",
                table: "tbl_sticker",
                column: "name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_tweet_title",
                table: "tbl_tweet",
                column: "title",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_tweet_userId",
                table: "tbl_tweet",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_tweetSticker_stickerId",
                table: "tbl_tweetSticker",
                column: "stickerId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_tweetSticker_tweetId",
                table: "tbl_tweetSticker",
                column: "tweetId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_user_login",
                table: "tbl_user",
                column: "login",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tbl_comment");

            migrationBuilder.DropTable(
                name: "tbl_tweetSticker");

            migrationBuilder.DropTable(
                name: "tbl_sticker");

            migrationBuilder.DropTable(
                name: "tbl_tweet");

            migrationBuilder.DropTable(
                name: "tbl_user");
        }
    }
}
