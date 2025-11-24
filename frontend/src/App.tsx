import { useState } from 'react';
import { Sidebar } from './components/Sidebar';
import { LabelsPanel } from './components/LabelsPanel';
import { PlaylistsPanel } from './components/PlaylistsPanel';
import { SearchPanel } from './components/SearchPanel';
import { PlaylistCreatorPanel } from './components/PlaylistCreatorPanel';
import { LandingPage } from './components/LandingPage';

export type Label = {
  id: string;
  name: string;
  color: string;
};

export type Track = {
  id: string;
  name: string;
  artist: string;
  album: string;
  duration: string;
  imageUrl: string;
  labels: string[];
};

export type Playlist = {
  id: string;
  name: string;
  trackCount: number;
  imageUrl: string;
};

export type User = {
  id: string;
  name: string;
  avatarUrl: string;
};

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<User | null>(null);
  const [activePanel, setActivePanel] = useState<'labels' | 'playlists' | 'search' | 'creator'>('labels');
  const [labels, setLabels] = useState<Label[]>([
    { id: '1', name: 'Workout', color: '#1DB954' },
    { id: '2', name: 'Chill', color: '#1E3A8A' },
    { id: '3', name: 'Focus', color: '#9333EA' },
    { id: '4', name: 'Party', color: '#DC2626' },
  ]);

  const [tracks, setTracks] = useState<Track[]>([
    {
      id: '1',
      name: 'Blinding Lights',
      artist: 'The Weeknd',
      album: 'After Hours',
      duration: '3:20',
      imageUrl: 'https://images.unsplash.com/photo-1614613535308-eb5fbd3d2c17?w=300&h=300&fit=crop',
      labels: ['1', '4'],
    },
    {
      id: '2',
      name: 'Levitating',
      artist: 'Dua Lipa',
      album: 'Future Nostalgia',
      duration: '3:23',
      imageUrl: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=300&h=300&fit=crop',
      labels: ['4'],
    },
    {
      id: '3',
      name: 'Weightless',
      artist: 'Marconi Union',
      album: 'Weightless',
      duration: '8:10',
      imageUrl: 'https://images.unsplash.com/photo-1511379938547-c1f69419868d?w=300&h=300&fit=crop',
      labels: ['2', '3'],
    },
  ]);

  const addLabel = (name: string, color: string) => {
    const newLabel: Label = {
      id: Date.now().toString(),
      name,
      color,
    };
    setLabels([...labels, newLabel]);
  };

  const deleteLabel = (id: string) => {
    setLabels(labels.filter(label => label.id !== id));
    // Remove label from all tracks
    setTracks(tracks.map(track => ({
      ...track,
      labels: track.labels.filter(labelId => labelId !== id),
    })));
  };

  const toggleTrackLabel = (trackId: string, labelId: string) => {
    setTracks(tracks.map(track => {
      if (track.id === trackId) {
        const hasLabel = track.labels.includes(labelId);
        return {
          ...track,
          labels: hasLabel
            ? track.labels.filter(id => id !== labelId)
            : [...track.labels, labelId],
        };
      }
      return track;
    }));
  };

  const addTrack = (track: Track) => {
    setTracks([...tracks, track]);
  };

  const handleLogin = () => {
    // Mock login - in production, this would redirect to Spotify OAuth
    setIsAuthenticated(true);
    setUser({
      id: '1',
      name: 'John Doe',
      avatarUrl: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100&h=100&fit=crop',
    });
    setActivePanel('labels');
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    setUser(null);
  };

  if (!isAuthenticated) {
    return <LandingPage onLogin={handleLogin} />;
  }

  return (
    <div className="flex h-screen bg-black text-white">
      <Sidebar 
        activePanel={activePanel} 
        onPanelChange={setActivePanel}
        user={user}
        onLogout={handleLogout}
      />
      
      <main className="flex-1 overflow-y-auto">
        {activePanel === 'labels' && (
          <LabelsPanel labels={labels} onAddLabel={addLabel} onDeleteLabel={deleteLabel} tracks={tracks} />
        )}
        {activePanel === 'playlists' && (
          <PlaylistsPanel 
            tracks={tracks} 
            labels={labels} 
            onToggleLabel={toggleTrackLabel}
          />
        )}
        {activePanel === 'search' && (
          <SearchPanel 
            labels={labels} 
            onToggleLabel={toggleTrackLabel}
            onAddTrack={addTrack}
            tracks={tracks}
          />
        )}
        {activePanel === 'creator' && (
          <PlaylistCreatorPanel tracks={tracks} labels={labels} />
        )}
      </main>
    </div>
  );
}