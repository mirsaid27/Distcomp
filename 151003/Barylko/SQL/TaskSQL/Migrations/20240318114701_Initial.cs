using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace TaskSQL.Migrations
{
    /// <inheritdoc />
    public partial class Initial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tbl_users",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Login = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Password = table.Column<string>(type: "character varying(128)", maxLength: 128, nullable: false),
                    FirstName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    LastName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_tags",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Name = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_tags", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_issues",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    UserId = table.Column<long>(type: "bigint", nullable: true),
                    Title = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    Created = table.Column<DateTime>(type: "timestamp with time zone", nullable: true),
                    Modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_issues", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_issues_tbl_users_UserId",
                        column: x => x.UserId,
                        principalTable: "tbl_users",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateTable(
                name: "TagIssue",
                columns: table => new
                {
                    TagsId = table.Column<long>(type: "bigint", nullable: false),
                    IssuesId = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TagIssue", x => new { x.TagsId, x.IssuesId });
                    table.ForeignKey(
                        name: "FK_TagIssue_tbl_tags_TagsId",
                        column: x => x.TagsId,
                        principalTable: "tbl_tags",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_TagIssue_tbl_issues_IssuesId",
                        column: x => x.IssuesId,
                        principalTable: "tbl_issues",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_comments",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    IssueId = table.Column<long>(type: "bigint", nullable: true),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_comments", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_comments_tbl_issues_IssueId",
                        column: x => x.IssueId,
                        principalTable: "tbl_issues",
                        principalColumn: "Id");
                });

            migrationBuilder.CreateIndex(
                name: "IX_TagIssue_issuesId",
                table: "TagIssue",
                column: "issuesId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_users_Login",
                table: "tbl_users",
                column: "Login",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_comments_IssueId",
                table: "tbl_comments",
                column: "IssueId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_tags_Name",
                table: "tbl_tags",
                column: "Name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_issues_CreatorId",
                table: "tbl_issues",
                column: "CreatorId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_issues_Title",
                table: "tbl_issues",
                column: "Title",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "TagIssue");

            migrationBuilder.DropTable(
                name: "tbl_comments");

            migrationBuilder.DropTable(
                name: "tbl_tags");

            migrationBuilder.DropTable(
                name: "tbl_issues");

            migrationBuilder.DropTable(
                name: "tbl_users");
        }
    }
}
