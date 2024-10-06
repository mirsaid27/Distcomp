package controllers

import (
	"github.com/gin-gonic/gin"

	"DC-eremeev/app/domain"
	"DC-eremeev/app/dto"
	"DC-eremeev/db"
)

func GetPosts(c *gin.Context) ([]domain.Post, error) {
	var posts []domain.Post
	db.Connection().Find(&posts)
	return posts, nil
}

func GetPost(c *gin.Context, req *dto.SingleRecordRequest) (domain.IDAO, error) {
	return BaseGetEntity(c, req, &domain.Post{})
}

func PostPosts(c *gin.Context, req *dto.CreatePostRequest) (domain.IDAO, error) {
	return BasePostEntity(c, req)
}

func UpdatePost(c *gin.Context, req *dto.UpdatePostRequest) (domain.IDAO, error) {
	return BaseUpdateEntity(c, req)
}

func DeletePost(c *gin.Context, req *dto.SingleRecordRequest) error {
	return BaseDeleteEntity(c, &domain.Post{ID: req.ID})
}
