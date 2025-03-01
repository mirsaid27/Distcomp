package grpc

import (
	"github.com/Khmelov/Distcomp/251004/Sazonov/notice/internal/handler/grpc/notice"
	"github.com/Khmelov/Distcomp/251004/Sazonov/notice/internal/service"
	"google.golang.org/grpc"
)

type grpcRegistrar interface {
	Register(reg grpc.ServiceRegistrar)
}

type grpcHandler struct {
	notice grpcRegistrar
}

func New(service service.Service) *grpcHandler {
	return &grpcHandler{
		notice: notice.New(service),
	}
}

func (h *grpcHandler) Register(reg grpc.ServiceRegistrar) {
	h.notice.Register(reg)
}
