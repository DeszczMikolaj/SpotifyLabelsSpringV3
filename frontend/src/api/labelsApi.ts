import api from "./api";
import { Label } from "../types/Label";

const BASE_URL = "/api/labels";

export async function getLabels(): Promise<Label[]> {
  const res = await api.get<Label[]>(BASE_URL);
  return res.data;
}

export async function createLabel(name: string, colorHex: string): Promise<Label> {
  const res = await api.post<Label>(BASE_URL, { name, colorHex });
  return res.data;
}

export async function deleteLabel(id: number): Promise<void> {
  await api.delete(`${BASE_URL}/${id}`);
}
