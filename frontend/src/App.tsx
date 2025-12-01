import { useState } from 'react';
import {useEffect } from 'react';
import { Sidebar } from './components/Sidebar';
import { LabelsPanel } from './components/LabelsPanel';
import { PlaylistsPanel } from './components/PlaylistsPanel';
import { SearchPanel } from './components/SearchPanel';
import { PlaylistCreatorPanel } from './components/PlaylistCreatorPanel';
import { LandingPage } from './components/LandingPage';
import api from "./api/api";
import { createLabel } from "./api/labelsApi";
import { Label } from "./types";


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
  tracksCount: number;
  imageUrl: string;
  tracks: Track[];
};

export type User = {
  id: string;
  name: string;
  email: string;
  avatarUrl: string;
  authenticated: boolean;
};

export default function App() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  const [activePanel, setActivePanel] = useState<'labels' | 'playlists' | 'search' | 'creator'>('labels');
  const [labels, setLabels] = useState<Label[]>([]);

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

  useEffect(() => {
      api
        .get("/api/me")
        .then((response) => {
          setUser(response.data);
          setLoading(false);
        })
        .catch((error) => {
          console.error("API error:", error);
          setUser({authenticated: false, name: null, email: null, id: null });
          setLoading(false);
        });

      api
        .get("/api/labels")
        .then((response) => {
          setLabels(response.data);
        })
        .catch((error) => {
          console.error("API error:", error);
        });

    }, []);

  const addLabel = async (name: string, colorHex: string) => {
      try {
            const created  = await createLabel(name, colorHex);
            setLabels((prev) => [...prev, created]);
      }
      catch (err) {
            console.error("Failed to create label:", err);
      }
//     const newLabel: Label = {
//       id: Date.now().toString(),
//       name,
//       color,
//     };
//     setLabels([...labels, newLabel]);
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
    window.location.href = ("http://127.0.0.1:8080/oauth2/authorization/spotify")
  };

  const handleLogout = () => {
     window.location.href = ("http://127.0.0.1:8080/logout")
  };

  if (loading) {
       return (
         <div className="full-screen center">
           <p className="muted">Loading…</p>
         </div>
       );
  }

  if (!user?.authenticated) {
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