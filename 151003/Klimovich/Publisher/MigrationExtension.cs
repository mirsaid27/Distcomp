using Microsoft.EntityFrameworkCore;
using Publisher.Repositories;

namespace Publisher
{
    public static class MigrationExtension
    {
        public static void ApplyMigration(this IApplicationBuilder app)
        {
            using IServiceScope scope = app.ApplicationServices.CreateScope();
            using ApplicationContext context = scope.ServiceProvider.GetRequiredService<ApplicationContext>();
            context.Database.Migrate();
        }
    }
}
