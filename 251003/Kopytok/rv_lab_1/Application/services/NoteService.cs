using Application.abstractions;
using Core;
using Database;
using DTO.requests;
using DTO.responces;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Application.services
{
    public class NoteService(AppDbContext appDbContext) : INoteService
    {
        private readonly AppDbContext _context = appDbContext;
        public async Task<NoteResponseTo?> CreateAsync(NoteRequestTo request)
        {
            var storyExists = await _context.Stories.AnyAsync(s => s.Id == request.StoryId);
            if (!storyExists)
                return null;
            var entity = new Note
            {
                StoryId = request.StoryId,
                Content = request.Content
            };

            var entry = await _context.Notes.AddAsync(entity);
            await _context.SaveChangesAsync();

            return new NoteResponseTo
            {
                Id = entry.Entity.Id,
                StoryId = entry.Entity.StoryId,
                Content = entry.Entity.Content
            };
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var entity = await _context.Notes.FindAsync(id);
            if (entity == null)
                return false;

            _context.Notes.Remove(entity);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<NoteResponseTo>> GetAllAsync()
        {
            var result = await _context.Notes.ToListAsync();
            return result.Select(x => new NoteResponseTo
            {
                Id = x.Id,
                StoryId = x.StoryId,
                Content = x.Content
            });
        }

        public async Task<NoteResponseTo?> GetByIdAsync(long id)
        {
            var entity = await _context.Notes.FindAsync(id);
            if (entity == null)
                return null;

            return new NoteResponseTo
            {
                Id = entity.Id,
                StoryId = entity.StoryId,
                Content = entity.Content
            };
        }

        public async Task<NoteResponseTo?> UpdateAsync(long id, NoteRequestTo request)
        {
            var entity = await _context.Notes.FindAsync(id);
            if (entity == null)
                return null;

            entity.StoryId = request.StoryId;
            entity.Content = request.Content;

            _context.Notes.Update(entity);
            await _context.SaveChangesAsync();

            return new NoteResponseTo
            {
                Id = entity.Id,
                StoryId = entity.StoryId,
                Content = entity.Content
            };
        }
    }
}
