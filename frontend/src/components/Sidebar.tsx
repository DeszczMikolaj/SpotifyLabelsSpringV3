import { Tag, Music, Search, ListPlus, LogOut } from 'lucide-react';
import { Button } from './ui/button';
import { User } from '../App';

type SidebarProps = {
  activePanel: 'labels' | 'playlists' | 'search' | 'creator';
  onPanelChange: (panel: 'labels' | 'playlists' | 'search' | 'creator') => void;
  user: User | null;
  onLogout: () => void;
};

export function Sidebar({ activePanel, onPanelChange, user, onLogout }: SidebarProps) {
  const navItems = [
    { id: 'labels' as const, label: 'Labels', icon: Tag },
    { id: 'playlists' as const, label: 'Playlists', icon: Music },
    { id: 'search' as const, label: 'Search', icon: Search },
    { id: 'creator' as const, label: 'Playlist Creator', icon: ListPlus },
  ];

  return (
    <aside className="w-64 bg-black border-r border-white/10 flex flex-col">
      <div className="p-6">
        <h1 className="flex items-center gap-2">
          <Music className="size-8 text-[#1DB954]" />
          <span>Spotify Labels</span>
        </h1>
      </div>

      <nav className="flex-1 px-3">
        <div className="space-y-1">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = activePanel === item.id;
            
            return (
              <button
                key={item.id}
                onClick={() => onPanelChange(item.id)}
                className={`w-full flex items-center gap-3 px-4 py-3 rounded-md transition-colors ${
                  isActive
                    ? 'bg-white/10 text-white'
                    : 'text-white/70 hover:text-white hover:bg-white/5'
                }`}
              >
                <Icon className="size-5" />
                <span>{item.label}</span>
              </button>
            );
          })}
        </div>
      </nav>

      <div className="p-4 space-y-4 border-t border-white/10">
        {user && (
          <div className="flex items-center gap-3 px-2">
            <img 
              src={user.avatarUrl} 
              alt={user.name}
              className="size-10 rounded-full object-cover"
            />
            <div className="flex-1 min-w-0">
              <p className="text-sm truncate">{user.name}</p>
              <p className="text-xs text-white/60">Premium</p>
            </div>
          </div>
        )}
        
        <Button
          variant="ghost"
          className="w-full justify-start gap-3 text-white/70 hover:text-white hover:bg-white/5"
          onClick={onLogout}
        >
          <LogOut className="size-5" />
          <span>Log out</span>
        </Button>
      </div>
    </aside>
  );
}