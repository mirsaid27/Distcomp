using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Core
{
    public class Note
    {
        [Column("id")]
        public long Id { get; set; } = 0;
        [Column("storyId")]
        public long StoryId { get; set; } = 0;
        [Column("content")]
        public String Content { get; set; } = string.Empty;
    }
}
