using AutoMapper;
using Discussion.Common;
using Discussion.CommentEntity.Dto;
using Discussion.CommentEntity.Interface;

namespace Discussion.CommentEntity
{
    public class CommentService(IMapper mapper, ICommentRepository repository)
        : AbstractCrudService<Comment, CommentRequestTO, CommentResponseTO>(mapper, repository), ICommentService
    {
        public override async Task<CommentResponseTO> Add(CommentRequestTO post)
        {
            if (!Validate(post))
            {
                throw new InvalidDataException("COMMENT is not valid");
            }

            return await base.Add(post);
        }

        public override async Task<CommentResponseTO> Update(CommentRequestTO post)
        {
            if (!Validate(post))
            {
                throw new InvalidDataException($"UPDATE invalid data: {post}");
            }

            return await base.Update(post);
        }

        public Task<IList<Comment>> GetByIssueID(int issueId)
        {
            throw new NotImplementedException();
        }

        private static bool Validate(CommentRequestTO post)
        {
            var contentLen = post.Content.Length;

            if (contentLen < 2 || contentLen > 2048)
            {
                return false;
            }
            return true;
        }
    }
}
